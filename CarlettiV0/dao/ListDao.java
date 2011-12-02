package dao;

import java.util.ArrayList;
import java.util.List;

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
public class ListDao implements DAO {
	private static ListDao listDao;
	private ArrayList<Palle> paller;
	private ArrayList<Mellemvare> mellemvarer;

	private ArrayList<Produkttype> produkttyper;
	private ArrayList<MellemlagerPlads> mellemlagerPladser;
	private ArrayList<Behandling> behandlinger;

	private ListDao() {
		paller = new ArrayList<Palle>();
		mellemvarer = new ArrayList<Mellemvare>();
		produkttyper = new ArrayList<Produkttype>();
		mellemlagerPladser = new ArrayList<MellemlagerPlads>();
		behandlinger = new ArrayList<Behandling>();
	}

	public static ListDao getListDao() {
		if (listDao == null) {
			listDao = new ListDao();
		}
		return listDao;
	}

	@Override
	public List<Palle> paller() {
		return new ArrayList<Palle>(paller);
	}

	@Override
	public List<Mellemvare> mellemvarer() {
		return new ArrayList<Mellemvare>(mellemvarer);
	}

	@Override
	public List<Produkttype> produkttyper() {
		return new ArrayList<Produkttype>(produkttyper);
	}

	@Override
	public List<MellemlagerPlads> mellemlagerPladser() {
		return new ArrayList<MellemlagerPlads>(mellemlagerPladser);
	}

	@Override
	public void gemPalle(Palle palle) {
		if (!paller.contains(palle))
			paller.add(palle);

	}

	@Override
	public void removePalle(Palle palle) {
		paller.remove(palle);

	}

	@Override
	public void gemMellemvare(Mellemvare mellemvare) {
		if (!mellemvarer.contains(mellemvare))
			mellemvarer.add(mellemvare);

	}

	@Override
	public void removeMellemvare(Mellemvare mellemvare) {
		mellemvarer.remove(mellemvare);

	}

	@Override
	public void gemProdukttype(Produkttype produkttype) {
		if (!produkttyper.contains(produkttype))
			produkttyper.add(produkttype);

	}

	@Override
	public void removeProdukttype(Produkttype produkttype) {
		produkttyper.remove(produkttype);

	}

	@Override
	public void gemMellemlagerPlads(MellemlagerPlads mellemlagerPlads) {
		if (!mellemlagerPladser.contains(mellemlagerPlads))
			mellemlagerPladser.add(mellemlagerPlads);

	}

	@Override
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerplads) {
		mellemlagerPladser.remove(mellemlagerplads);

	}

	@Override
	public List<Behandling> behandlinger() {
		return new ArrayList<Behandling>(behandlinger);
	}

	@Override
	public void gemBehandling(Behandling behandling) {
		behandlinger.add(behandling);

	}

	@Override
	public void removeBehandling(Behandling behandling) {
		behandlinger.remove(behandling);

	}

	@Override
	public Palle soegPalle(String stregkode) {
		boolean found = false;
		int i = 0;

		while (!found && i < paller.size()) {
			if (paller.get(i).getStregkode().equals(stregkode)) {
				found = true;
			} else {
				i++;
			}
		}
		if (found) {
			return paller.get(i);
		} else {
			return null;
		}
	}

	@Override
	public MellemlagerPlads soegMellemlagerPlads(String stregkode) {
		boolean found = false;
		int i = 0;

		while (!found && i < mellemlagerPladser.size()) {
			if (mellemlagerPladser.get(i).getStregkode().equals(stregkode)) {
				found = true;
			} else {
				i++;
			}
		}
		if (found) {
			return mellemlagerPladser.get(i);
		} else {
			return null;
		}
	}

	@Override
	public void close() {
		// DO NOTHING

	}

	@Override
	public List<Mellemvare> faerdigvarer() {
		ArrayList<Mellemvare> faerdigvarer = new ArrayList<Mellemvare>();
		for (int i = 0; i < mellemvarer.size(); i++) {
			if (mellemvarer.get(i).getStatus() == MellemvareStatus.FAERDIG) {
				faerdigvarer.add(mellemvarer.get(i));
			}
		}
		return faerdigvarer;
	}

	@Override
	public List<Mellemvare> kasseredeVarer() {
		ArrayList<Mellemvare> kasseredeVarer = new ArrayList<Mellemvare>();
		for (int i = 0; i < mellemvarer.size(); i++) {
			if (mellemvarer.get(i).getStatus() == MellemvareStatus.KASSERET) {
				kasseredeVarer.add(mellemvarer.get(i));
			}
		}
		return kasseredeVarer;
	}

	@Override
	public void opdaterDatabase() {
		// DO NOTHING

	}

}
