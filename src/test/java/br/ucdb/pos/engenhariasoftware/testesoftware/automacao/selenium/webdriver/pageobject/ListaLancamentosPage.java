package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;

public class ListaLancamentosPage {

    private WebDriver driver;

    public ListaLancamentosPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void acessar() {
        driver.get("http://localhost:8080/lancamentos");
    }


    public void buscar(final String descricaoLancamento) {
        WebElement descricao = driver.findElement(By.id("itemBusca"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);
        driver.findElement(By.id("bth-search")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"divTabelaLancamentos\"]")));
    }

    /**Botão Recarregar */
    public void recarregar() {
        driver.findElement(By.id("recarregar")).click();
    }

    /**Botão "Novo" */
    public void novoLancamento() {
        driver.findElement(By.id("novoLancamento")).click();
    }

    /**Botão "Editar" */
    public void editarLancamento() {
        driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[1]/td[6]/div/a[contains(@href,'editar')]")).click();
    }

    /**Botão "Excluir"  */
    public void excluirLancamento() {
        driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[1]/td[6]/div/a[contains(@href,'remover')]")).click();
    }

    /** Botão de Relatórios */
    public void acessarRelatorios() {
        driver.findElement(By.xpath("//*[@id=\"form-busca\"]/div[1]/div[2]/div[4]/a")).click();
    }


    public BigDecimal getTotalEntradaRodape() {
        return this.convertStringToBigDecimal(driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tfoot/tr[2]/th/span")).getText());
    }


    public BigDecimal getTotalSaidaRodape() {
        return this.convertStringToBigDecimal(driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tfoot/tr[1]/th/span")).getText());
    }


    public BigDecimal getTotalPorTipoTabela(TipoLancamento tipo) {
        List<WebElement> valores = driver.findElements(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr/td[4]"));
        BigDecimal total = BigDecimal.ZERO;
        int i = 1;
        for (WebElement valor : valores) {
            String valorLinha = valor.getText();
            String tipoLinha = driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[" + i + "]/td[5]")).getText();
            if (tipoLinha.equals(tipo.getDescricao())) {
                total = total.add(this.convertStringToBigDecimal(valorLinha));
            }
            i++;
        }
        return total;
    }



    public boolean existeLancamento(Lancamento lancamento) {
        String lancamentos = driver.findElement(By.id("tabelaLancamentos")).getText();
        return (lancamentos.contains(lancamento.getDescricao()) &&
                lancamentos.contains(lancamento.getValorFormatado()) &&
                lancamentos.contains(lancamento.getDataLancamentoFormatado()) &&
                lancamentos.contains(lancamento.getTipoLancamento().getDescricao()) &&
                lancamentos.contains(lancamento.getCategoria().getNome())
        );
    }


    private BigDecimal convertStringToBigDecimal(String numberString) {
        if (numberString == null || numberString.equals("") || numberString.trim().replace("R$", "").equals("")) {
            return null;
        }
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            String pattern = "#,##0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return new BigDecimal(decimalFormat.parse(numberString.replace("R$ ", "")).toString());
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

}

