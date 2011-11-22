package dao;

import java.util.List;

import model.Behandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;

public interface DAO {
	public List<Palle> paller();
	public List<Mellemvare> mellemvarer();
	public List<Produkttype> produkttyper();
	public List<MellemlagerPlads>mellemlagerPladser();
	public List<Behandling>behandlinger();
	
	public void gemPalle(Palle palle);
	public void removePalle(Palle palle);
	
	public void gemMellemvare(Mellemvare mellemvare);
	public void removeMellemvare(Mellemvare mellemvare);
	
	public void gemProdukttype(Produkttype produkttype);
	public void removeProdukttype(Produkttype produkttype);
	
	public void gemMellemlagerPlads(MellemlagerPlads mellemlagerPlads);
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerplads);
	
	public void gemBehandling(Behandling behandling);
	public void removeBehandling(Behandling behandling);
	
	public void close();
	
}
