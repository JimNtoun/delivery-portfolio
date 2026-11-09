param(
    [string]$BackendUrl = 'http://127.0.0.1:8080',
    [string]$FrontendUrl = 'http://127.0.0.1:4200'
)
$ErrorActionPreference = 'Stop'
$checks = @(
    @{Path='/stores'; Minimum=1},
    @{Path='/stores?storeId=1'; Minimum=1},
    @{Path='/orders'; Minimum=1},
    @{Path='/stores/reportFamousStores'; Minimum=1},
    @{Path='/stores/reportTop10Products'; Minimum=1}
)
foreach ($check in $checks) {
    $response = Invoke-WebRequest -Uri ($BackendUrl.TrimEnd('/') + $check.Path) -TimeoutSec 15 -UseBasicParsing
    if ($response.StatusCode -ne 200) { throw "Unexpected HTTP status for $($check.Path)" }
    $payload = $response.Content | ConvertFrom-Json
    if (@($payload.data).Count -lt $check.Minimum -or $null -eq $payload.data) { throw "Missing demo data for $($check.Path)" }
    Write-Output "PASS GET $($check.Path)"
}
$store = Invoke-RestMethod -Uri ($BackendUrl.TrimEnd('/') + '/stores?storeId=1') -TimeoutSec 15
if (@($store.data.products).Count -lt 1) { throw 'Store has no menu products' }
Write-Output 'PASS Store menu contains products'
$page = Invoke-WebRequest -Uri ($FrontendUrl.TrimEnd('/') + '/userhome') -Headers @{Accept='text/html'} -TimeoutSec 15 -UseBasicParsing
if ($page.StatusCode -ne 200 -or $page.Content -notmatch '<app-root') { throw 'Frontend application shell is unavailable' }
Write-Output 'PASS Frontend application shell (not a browser/end-to-end test)'
Write-Output 'Baseline checks passed. These checks do not certify authentication or checkout.'
