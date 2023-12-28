package com.lorente.jeremy.persistencia;

import com.lorente.jeremy.modelos.Empleado;
import com.lorente.jeremy.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Clase EmpleadoJpaController representa un controlador que permite manejar las
 * operaciones CRUD con la entidad Empleado en la base de datos.
 *
 */
public class EmpleadoJpaController implements Serializable {

    /**
     * Constructor predeterminado que inicializa la instancia de
     * EntityManagerFactory
     */
    public EmpleadoJpaController() {
        emf = Persistence.createEntityManagerFactory("EmpleadoPersistence");
    }

    /**
     * Constructor que permite especificar un EntityManagerFactory
     *
     * @param emf EntityManagerFactory a utilizar
     */
    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea un nuevo registro de empleado en la BD si el DNI no existe
     *
     * @param empleado Objeto Empleado que se creara en la BD.
     */
    public void create(Empleado empleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();

            if (existEmpleadoDni(empleado.getDni())) {
                return;
            }

            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Modifica un registro existente de empleado en la base de datos.
     *
     * @param empleado Objeto Empleado que se creara en la BD
     * @throws NonexistentEntityException Si el empleado no existe.
     * @throws Exception Si hay algun error durante la edicion.
     */
    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            empleado = em.merge(empleado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina un registro existente de empleado en la base de datos.
     *
     * @param id Identificador del empleado a borrar.
     * @throws NonexistentEntityException Si el empleado no existe.
     */
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Metodo que busca y devuelve la lista de registros de todos los empleados
     *
     * @return Lista de objetos Empleado
     */
    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Metodo que busca y devuelve un registro de Empleado segun su ID
     *
     * @param id Identificador del empleado a buscar
     * @return Objeto Empleado
     */
    public Empleado findEmpleado(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Metodo que busca y devuelve una lista de registros de Empleado segun su
     * cargo
     *
     * @param charge Cargo de los Empleados a buscar
     * @return Lista de objetos Empleado
     */
    public List<Empleado> findEmpleadoByCharge(String charge) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Empleado> query = em.createQuery("SELECT e FROM Empleado e WHERE LOWER(e.cargo) = :charge", Empleado.class);
            query.setParameter("charge", charge.toLowerCase());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Metodo que cuenta el numero de registros de Empleado en la BD
     *
     * @return Cantidad de registros en la BD
     */
    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * Metodo que cuenta el numero de empleados con X dni
     *
     * @return Boleano para verificar si ya hay un registro
     */
    public boolean existEmpleadoDni(String dni) {
        EntityManager em = getEntityManager();
        Long count = (Long) em.createQuery("SELECT COUNT(e) FROM Empleado e WHERE e.dni= :dni")
                .setParameter("dni", dni)
                .getSingleResult();

        return count > 0;
    }
}
