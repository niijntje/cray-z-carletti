package dao;

import java.util.List;

import model.Behandling;
import model.Delbehandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;

public class JpaDao implements DAO{
	private static JpaDao dao;
	
	public JpaDao(){
		
	}
	
	public static JpaDao getDao(){
		if(dao == null){
			dao = new JpaDao();
		}
		return dao;
	}

	@Override
	public List<Palle> paller() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mellemvare> mellemvarer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produkttype> produkttyper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MellemlagerPlads> mellemlagerPladser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Behandling> behandlinger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gemPalle(Palle palle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePalle(Palle palle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gemMellemvare(Mellemvare mellemvare) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMellemvare(Mellemvare mellemvare) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gemProdukttype(Produkttype produkttype) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProdukttype(Produkttype produkttype) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gemMellemlagerPlads(MellemlagerPlads mellemlagerPlads) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerplads) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gemBehandling(Behandling behandling) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBehandling(Behandling behandling) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gemDelbehandling(Delbehandling d) {
		// TODO Auto-generated method stub
		
	}
}
