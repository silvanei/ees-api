package org.ees.api.agenda.infra.service;

import java.sql.SQLException;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.Conexao;
import org.ees.api.agenda.infra.repository.AcessoRepositoryImpl;
import org.ees.api.agenda.infra.repository.FuncionarioRepositoryImpl;
import org.ees.api.agenda.infra.repository.SalaoRepositoryImpl;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.service.SalaoService;

public class SalaoServiceImpl implements SalaoService {
	
	private SalaoRepository salaoRepository = new SalaoRepositoryImpl();
	private FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryImpl();
	private AcessoRepositoryImpl acessoRepository = new AcessoRepositoryImpl();

	@Override
	public Salao registrarSalao(Salao salao, Funcionario administrador){
		
		try {
			
			Conexao.getConexao().setAutoCommit(false);
			
			Salao newSalao =  salaoRepository.insert(salao);
			Funcionario newFuncionario = funcionarioRepository.insert(administrador, newSalao.getId());
			Acesso newAcesso = acessoRepository.insert(administrador.getAcesso(), newFuncionario.getId());
			
			newFuncionario.setAcesso(newAcesso);
			newSalao.getFuncionarios().add(newFuncionario);
			
			Conexao.getConexao().commit();
			
			return salao;
			
		} catch (Exception e) {
			try {
				Conexao.getConexao().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			};
			return null;
		} finally {
			try {
				Conexao.getConexao().setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
