package org.sergei.parser.model.jpa.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomCrudRepository<S> {
    void addRecord(S entity);
}
