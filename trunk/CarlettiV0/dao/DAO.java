package dao;

import java.util.List;

import model.Behandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;

/**
 * 
 * @author Rasmus Cederdorff
 * 
 */
public interface DAO {
	/**
	 * Returnerer en liste med alle behandlinger af typen Behandling
	 * @return
	 */
	public List<Behandling> behandlinger();
	
	/**
	 * Lukker forbindelsen til databasen
	 * når der bruges JPA-dao
	 */
	public void close();

	/**
	 * Returnerer en liste med alle faerdigvarer af typen Mellemvare
	 * @return
	 */
	public List<Mellemvare> faerdigvarer();

	/**
	 * Gemmer en Behandling
	 * @param behandling
	 */
	public void gemBehandling(Behandling behandling);

	/**
	 * Gemmer MellemlagerPlads
	 * @param mellemlagerPlads
	 */
	public void gemMellemlagerPlads(MellemlagerPlads mellemlagerPlads);

	/**
	 * Gemmer Mellemvare
	 * @param mellemvare
	 */
	public void gemMellemvare(Mellemvare mellemvare);
	
	/**
	 * Gemmer Palle
	 * @param palle
	 */
	public void gemPalle(Palle palle);
	
	/**
	 * Gemmer Produkttype i databasen
	 * @param produkttype
	 */
	public void gemProdukttype(Produkttype produkttype);

	/**
	 * Returnerer en liste med alle kasserede varer af typen Mellemvare
	 * @return
	 */
	public List<Mellemvare> kasseredeVarer();

	/**
	 * Returnerer en liste med alle mellemlagerPladser af typen MellemlagerPlads
	 * @return
	 */
	public List<MellemlagerPlads> mellemlagerPladser();

	/**
	 * Returnerer en liste med alle mellemvarer af typen Mellemvare
	 * @return
	 */
	public List<Mellemvare> mellemvarer();

	/**
	 * Opdaterer SQL-databasen ved at kalde begin() og commit()
	 */
	public void opdaterDatabase();

	/**
	 * Returnerer en liste med alle paller af typen Palle
	 * @return
	 */
	public List<Palle> paller();
	
	/**
	 * Returnerer en liste med alle produkttyper af typen Produkttype
	 * @return
	 */
	public List<Produkttype> produkttyper();

	/**
	 * Fjerner en given Behandling fra databasen
	 * @param behandling
	 */
	public void removeBehandling(Behandling behandling);

	/**
	 * Fjerner en given MellemlagerPlads fra databasen
	 * @param mellemlagerplads
	 */
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerplads);

	/**
	 * Fjerner en given Mellemvare fra databasen
	 * @param mellemvare
	 */
	public void removeMellemvare(Mellemvare mellemvare);

	/**
	 * Fjerner en given Palle fra databasen
	 * @param palle
	 */
	public void removePalle(Palle palle);

	/**
	 * Fjerner en given Produkttype fra databasen
	 * @param produkttype
	 */
	public void removeProdukttype(Produkttype produkttype);

	/**
	 * Søger efter en given MellemlagerPlads og tager en
	 * @param stregkode
	 * @return Mellemvare
	 */
	public MellemlagerPlads soegMellemlagerPlads(String stregkode);

	/**
	 * Søger efter en given 
	 * @param stregkode
	 * @return
	 */
	public Palle soegPalle(String stregkode);

	/**
	 * Retunerer en liste med alle varer der er under behandling af typen Mellemvare
	 * @return
	 */
	public List<Mellemvare> varerUnderBehandling();

}
