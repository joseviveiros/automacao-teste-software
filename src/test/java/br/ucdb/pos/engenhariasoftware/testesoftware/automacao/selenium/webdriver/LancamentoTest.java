package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LancamentoTest {

    protected WebDriver driver;
    protected ListaLancamentosPage listaLancamentosPage;
    protected LancamentoPage lancamentoPage;
    protected Lancamento lancamento;

    @BeforeClass
    protected void inicializa() {
        boolean windows = System.getProperty("os.name").toUpperCase().contains("WIN");
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/src/test/resources/drivers/" +
                        "/geckodriver" + (windows ? ".exe" : ""));
        driver = new FirefoxDriver();
        listaLancamentosPage = new ListaLancamentosPage(driver);
        lancamentoPage = new LancamentoPage(driver);
        lancamento = new LancamentoBuilder().random().build();
    }

    /** Cria e valida um lançamento  */
    public void criarLancamento() {
        listaLancamentosPage.acessar();
        listaLancamentosPage.novoLancamento();
        lancamentoPage.cria(lancamento);
        listaLancamentosPage.buscar(lancamento.getDescricao());
        assertTrue(listaLancamentosPage.existeLancamento(lancamento), "Erro ao tentar criar um lançamento.");
    }

    /** Edita e valida a edição de um lançamento */
    public void editarLancamento() {
        listaLancamentosPage.acessar();
        listaLancamentosPage.buscar(lancamento.getDescricao());
        listaLancamentosPage.editarLancamento();
        lancamento.setDescricao(lancamentoPage.edita());
        listaLancamentosPage.buscar(lancamento.getDescricao());
        assertTrue(listaLancamentosPage.existeLancamento(lancamento), "Erro ao tentar editar um lançamento.");
    }

    /** Exclui e valida a exclusão de um lançamento  */
    public void excluirLancamento() {
        listaLancamentosPage.acessar();
        listaLancamentosPage.buscar(lancamento.getDescricao());
        listaLancamentosPage.excluirLancamento();
        listaLancamentosPage.buscar(lancamento.getDescricao());
        assertFalse(listaLancamentosPage.existeLancamento(lancamento), "Erro ao tentar excluir um lançamento.");
    }

    /** Valida a obrigatoriedade dos campos do formulário estão sendo mostradas na tela de cadastro  */
    public void validarCamposObrigatorios() {
        listaLancamentosPage.acessar();
        listaLancamentosPage.novoLancamento();
        lancamentoPage.salvar();
        assertEquals(lancamentoPage.getQtdeMensagensCamposObrigatorios(), lancamentoPage.getQtdeInputs(), "Erro ao validar os campos obrigatórios.");

    }

    /** Valida o total de entrada da página atual, verificando o total de entrada nas linhas da tabela com o total informado no rodapé     */
    public void validarTotalEntrada() {
        listaLancamentosPage.acessar();
        assertEquals(listaLancamentosPage.getTotalPorTipoTabela(TipoLancamento.ENTRADA), listaLancamentosPage.getTotalEntradaRodape(), "Erro ao validar o valor total de entrada.");

    }

    /** Valida o total de saída da página atual, verificando o total de saída nas linhas da tabela com o total informado no rodapé     */
    public void validarTotalSaida() {
        listaLancamentosPage.acessar();
        assertEquals(listaLancamentosPage.getTotalPorTipoTabela(TipoLancamento.SAIDA), listaLancamentosPage.getTotalSaidaRodape(), "Erro ao validar o valor total de saída");
    }

    /** Valida o acesso a página de relatórios    */
    public void acessarRelatorios() {
        listaLancamentosPage.acessar();
        listaLancamentosPage.acessarRelatorios();
        assertTrue(this.existeTituloPagina("Dashboard"), "Erro ao tentar acessar os relatórios!");
    }

    /** Verifica se o título informado existe na página atual     */
    public boolean existeTituloPagina(String titulo) {
        return driver.findElement(By.xpath("//div[contains(@class, 'panel-heading')]")).getText().contains(titulo);
    }

    @AfterClass
    protected void finaliza() {
        driver.quit();
    }

    /** Mapeamento de Lancamento  */
    static class LancamentoBuilder {
        private Lancamento lancamento;

        LancamentoBuilder() {
            lancamento = new Lancamento();
        }

        LancamentoBuilder setId(Long id) {
            lancamento.setId(id);
            return this;
        }

        LancamentoBuilder setData(LocalDateTime data) {
            lancamento.setDataLancamento(data);
            return this;
        }

        LancamentoBuilder setDescricao(String descricao) {
            lancamento.setDescricao(descricao);
            return this;
        }

        LancamentoBuilder setValor(double valor) {
            lancamento.setValor(BigDecimal.valueOf(valor));
            return this;
        }

        LancamentoBuilder setTipo(TipoLancamento tipo) {
            lancamento.setTipoLancamento(tipo);
            return this;
        }

        LancamentoBuilder setCategoria(Categoria categoria) {
            lancamento.setCategoria(categoria);
            return this;
        }

        private <T extends Enum<?>> T randomEnum(Class<T> classe) {
            final SecureRandom random = new SecureRandom();
            int x = random.nextInt(classe.getEnumConstants().length);
            return classe.getEnumConstants()[x];
        }

        LancamentoBuilder random() {
            Random rand = new Random();
            return setData(LocalDateTime.now())
                    .setTipo(randomEnum(TipoLancamento.class))
                    .setDescricao("Valor randomico para lançamento " + (new Timestamp(System.currentTimeMillis())))
                    .setValor(rand.nextInt(100_000) / 100.0)
                    .setCategoria(randomEnum(Categoria.class));
        }

        Lancamento build() {
            System.out.println("Valor randomico para lancamento criado:\n" + lancamento.toString());
            return lancamento;
        }
    }
}