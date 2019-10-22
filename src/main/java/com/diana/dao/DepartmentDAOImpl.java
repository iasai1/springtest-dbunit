package com.diana.dao;

import com.diana.model.Department;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional(propagation = Propagation.MANDATORY)
public class DepartmentDAOImpl implements  DepartmentDAO{

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Department findById(Long id) {
        return entityManager.createQuery("select d from Department d where d.id = :id", Department.class)
                .setParameter("id", id).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Department findByName(String name) {
        return entityManager.createQuery("select d from Department d where d.name = :name", Department.class)
                .setParameter("name", name).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Department> getAll() {
        return entityManager.createQuery("select d from Department d", Department.class).getResultList();
    }

    @Override
    public void add(Department department) {
        entityManager.persist(department);
    }

    @Override
    public void merge(Department department) {
        entityManager.merge(department);
    }
}
