package com.diana.dao;

import com.diana.model.Address;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Component
@Transactional(propagation = Propagation.MANDATORY)
public class AddressDAOImpl implements AddressDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Address findById(Long id) {
        return entityManager.createQuery("select a from Address a where a.id = :id", Address.class)
                .setParameter("id", id).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void add(Address address) {
        entityManager.persist(address);
    }

}
