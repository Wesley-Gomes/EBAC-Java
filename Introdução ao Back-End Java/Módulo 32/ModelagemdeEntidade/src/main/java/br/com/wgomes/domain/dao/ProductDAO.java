package br.com.wgomes.domain.dao;

import br.com.wgomes.config.JPAUtil;
import br.com.wgomes.domain.dao.base.GenericDAO;
import br.com.wgomes.domain.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductDAO implements GenericDAO<ProductEntity, Long> {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public Optional<ProductEntity> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return Optional.ofNullable(em.find(ProductEntity.class, id));
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class).getResultList();
        }
    }

    @Override
    public void save(ProductEntity entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public void update(ProductEntity entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long productId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ProductEntity entity = em.find(ProductEntity.class, productId);
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
