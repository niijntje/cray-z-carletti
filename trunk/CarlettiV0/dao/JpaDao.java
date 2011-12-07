package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Behandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.MellemvareStatus;
import model.Palle;
import model.Produkttype;

/**
 * 
 * @author Cederdorff
 * 
 */
public class JpaDao implements DAO {
	private static JpaDao dao;

	public JpaDao() {
		// singleton
	}

	public static JpaDao getDao() {
		if (dao == null) {
			dao = new JpaDao();
		}
		return dao;
	}

	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("CarlettiLageringssytem");
	private EntityManager em = emf.createEntityManager();
	private EntityTransaction tx = em.getTransaction();

	@Override
	public List<Palle> paller() {
		return em.createQuery("SELECT p FROM Palle p", Palle.class)
				.getResultList();
	}

	@Override
	public List<Mellemvare> mellemvarer() {
		return em.createQuery("SELECT m FROM Mellemvare m", Mellemvare.class)
				.getResultList();
	}

	@Override
	public List<Produkttype> produkttyper() {
		return em.createQuery("SELECT p FROM Produkttype p", Produkttype.class)
				.getResultList();
	}

	@Override
	public List<MellemlagerPlads> mellemlagerPladser() {
		return em.createQuery("SELECT m FROM MellemlagerPlads m",
				MellemlagerPlads.class).getResultList();
	}

	@Override
	public List<Behandling> behandlinger() {
		return em.createQuery("SELECT b FROM Behandling b", Behandling.class)
				.getResultList();
	}

	@Override
	public void gemPalle(Palle palle) {
		tx.begin();
		em.persist(palle);
		tx.commit();

	}

	@Override
	public void removePalle(Palle palle) {
		tx.begin();
		em.remove(palle);
		tx.commit();

	}

	@Override
	public void gemMellemvare(Mellemvare mellemvare) {
		tx.begin();
		em.persist(mellemvare);
		tx.commit();

	}

	@Override
	public void removeMellemvare(Mellemvare mellemvare) {
		tx.begin();
		em.remove(mellemvare);
		tx.commit();

	}

	@Override
	public void gemProdukttype(Produkttype produkttype) {
		tx.begin();
		em.persist(produkttype);
		tx.commit();

	}

	@Override
	public void removeProdukttype(Produkttype produkttype) {
		tx.begin();
		em.remove(produkttype);
		tx.commit();

	}

	@Override
	public void gemMellemlagerPlads(MellemlagerPlads mellemlagerPlads) {
		tx.begin();
		em.persist(mellemlagerPlads);
		tx.commit();

	}

	@Override
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerplads) {
		tx.begin();
		em.remove(mellemlagerplads);
		tx.commit();

	}

	@Override
	public void gemBehandling(Behandling behandling) {
		tx.begin();
		em.persist(behandling);
		tx.commit();

	}

	@Override
	public void removeBehandling(Behandling behandling) {
		tx.begin();
		em.remove(behandling);
		tx.commit();

	}

	@Override
	public Palle soegPalle(String stregkode) {
		Palle palle = null;
		String jplQuery = "SELECT p FROM Palle p WHERE p.stregkode = :stregkode";
		Query query = em.createQuery(jplQuery);
		query.setParameter("stregkode", stregkode);

		List<Palle> list = query.getResultList();
		if (list.size() > 0) {
			palle = list.get(0);
		}

		return palle;
	}

	@Override
	public MellemlagerPlads soegMellemlagerPlads(String stregkode) {
		MellemlagerPlads plads = null;
		String jplQuery = "SELECT m FROM MellemlagerPlads m WHERE m.stregkode = :stregkode";
		Query query = em.createQuery(jplQuery);
		query.setParameter("stregkode", stregkode);

		List<MellemlagerPlads> list = query.getResultList();
		if (list.size() > 0) {
			plads = list.get(0);
		}

		return plads;
	}

	@Override
	public void close() {
		em.clear();
	}

	@Override
	public List<Mellemvare> faerdigvarer() {
		return em
				.createQuery(
						"SELECT m FROM Mellemvare m WHERE m.status = :FAERDIG",
						Mellemvare.class)
				.setParameter("FAERDIG", MellemvareStatus.FAERDIG)
				.getResultList();
	}

	@Override
	public List<Mellemvare> kasseredeVarer() {
		return em
				.createQuery(
						"SELECT m FROM Mellemvare m WHERE m.status = :kasseret",
						Mellemvare.class)
				.setParameter("kasseret", MellemvareStatus.KASSERET)
				.getResultList();
	}

	@Override
	public List<Mellemvare> varerUnderBehandling() {
		return em
				.createQuery(
						"SELECT m FROM Mellemvare m WHERE m.status = :underbehandling",
						Mellemvare.class)
				.setParameter("underbehandling",
						MellemvareStatus.UNDERBEHANDLING).getResultList();
	}

	@Override
	public void opdaterDatabase() {
		tx.begin();
		tx.commit();
	}

}
