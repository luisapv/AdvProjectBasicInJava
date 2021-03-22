package br.com.project.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "PerfilPreConfiguracao.LitarTodos", query = "from PerfilPreConfiguracao")
})
public class PerfilPreConfiguracao implements Serializable {

	private static final long serialVersionUID = 4619732362105654087L;

	private Long id;
	private Login login;
	private EnumPaginas pagina;
	private List<String> columnOrderDataTable;
	private List<Boolean> columnVisibleDataTable;
	
	public PerfilPreConfiguracao() {}
	
	public PerfilPreConfiguracao(Login login, EnumPaginas pagina, List<String> columnOrderDataTable,
			List<Boolean> columnVisibleDataTable) {
		this.login = login;
		this.pagina = pagina;
		this.columnOrderDataTable = columnOrderDataTable;
		this.columnVisibleDataTable = columnVisibleDataTable;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="login")
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	@Enumerated(EnumType.STRING)
	public EnumPaginas getPagina() {
		return pagina;
	}
	
	public void setPagina(EnumPaginas pagina) {
		this.pagina = pagina;
	}

	@ElementCollection
	@CollectionTable(name = "PerfilPreConfiguacao_ColumnOrderDataTable", joinColumns = @JoinColumn(name = "user_page"))
    @Column(name = "Column_Name")
	public List<String> getColumnOrderDataTable() {
		return columnOrderDataTable;
	}
	
	public void setColumnOrderDataTable(List<String> columnOrderDataTable) {
		this.columnOrderDataTable = columnOrderDataTable;
	}
	
	@ElementCollection
	@CollectionTable(name = "PerfilPreConfiguacao_ColumnVisibleDataTable", joinColumns = @JoinColumn(name = "user_page"))
    @Column(name = "Column_Visible")
	public List<Boolean> getColumnVisibleDataTable() {
		return columnVisibleDataTable;
	}

	public void setColumnVisibleDataTable(List<Boolean> columnVisibleDataTable) {
		this.columnVisibleDataTable = columnVisibleDataTable;
	}
}
