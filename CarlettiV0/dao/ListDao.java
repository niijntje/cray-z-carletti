package dao;

import java.util.ArrayList;
import java.util.List;

import model.Behandling;
import model.Delbehandling;
import model.MellemlagerPlads;
import model.Mellemvare;
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
	private ArrayList<Delbehandling> delbehandlinger;

	private ListDao() {
		paller = new ArrayList<Palle>();
		mellemvarer = new ArrayList<Mellemvare>();
		produkttyper = new ArrayList<Produkttype>();
		mellemlagerPladser = new ArrayList<MellemlagerPlads>();
		behandlinger = new ArrayList<Behandling>();
		delbehandlinger = new ArrayList<Delbehandling>();
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
	public void close() {
		// DO NOTHING

	}

	@Override
	public void gemDelbehandling(Delbehandling d) {
		delbehandlinger.add(d);
		
	}

	public ArrayList<Palle> getPaller() {
		return paller;
	}
}
