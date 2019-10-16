package com.diana.dao;

import com.diana.model.Address;
import com.diana.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional(propagation = Propagation.MANDATORY)
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Employee findById(Long id) {
        return entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class)
                .setParameter("id", id).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Employee findFetchedById(Long id) {
        return entityManager.createQuery("select e from Employee e join fetch e.department where e.id = :id", Employee.class)
                .setParameter("id", id).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Employee findByName(String name) {
        return entityManager.createQuery("select e from Employee e where e.name = :name", Employee.class)
                .setParameter("name", name).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Employee> getAll() {
        return entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Override
    public void add(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public void delete(Employee employee) {
        Address a = entityManager.find(Address.class, employee.getId());
        entityManager.remove(a);
        entityManager.flush();
        entityManager.remove(employee);
    }

}
