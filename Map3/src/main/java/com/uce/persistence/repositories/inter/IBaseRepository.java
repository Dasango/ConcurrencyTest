package com.uce.persistence.repositories.inter;

public interface IBaseRepository<CLASS, ID> {
    void delete(ID id);

    CLASS save(CLASS obj);

    CLASS update(CLASS obj);

    CLASS findById(ID id);
}
