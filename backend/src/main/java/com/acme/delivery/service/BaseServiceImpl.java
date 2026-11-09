package com.acme.delivery.service;

import com.acme.delivery.base.BaseComponent;
import com.acme.delivery.domain.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseServiceImpl<T extends BaseModel> extends BaseComponent implements BaseService<T> {
    public abstract JpaRepository<T, Long> getRepository();

    @Override
    public T create(final T entity) {
        logger.debug("Creating {}.", entity);
        return getRepository().save(entity);
    }


    @Override
    public List<T> createAll(final List<T> entities) {
        return getRepository().saveAll(entities);
    }

    public List<T> createAll(final T... items) {
        return createAll(Arrays.asList(items));
    }

    public List<T> createAll(final T item) {
        return createAll(item);
    }
    @Override
    public void update(final T entity) {
        logger.debug("Updating {}.", entity);
        getRepository().save(entity);
    }

    @Override
    public void delete(final T entity) {
        final T entityFound = getRepository().getReferenceById(entity.getId());
        logger.debug("Deleting {}.", entityFound);
        getRepository().delete(entityFound);
    }

    @Override
    public void deleteById(final Long id) {
        final T entityFound = getRepository().getReferenceById(id);
        logger.debug("Deleting {}.", entityFound);
        getRepository().deleteById(id);
    }

    @Override
    public boolean exists(final T entity) {
        logger.trace("Entity whether {} exists ?", entity);
        return getRepository().existsById(entity.getId());
    }

    @Override
    public T get(final Long id) {
        logger.trace("Entity found matching id:{}.",id);
        return getRepository().findById(id).orElseThrow(() -> new NoSuchElementException("Element not found"));
    }

    @Override
    public List<T> findAll() {
        logger.trace("Returned {} items.");
        return getRepository().findAll();
    }
}
