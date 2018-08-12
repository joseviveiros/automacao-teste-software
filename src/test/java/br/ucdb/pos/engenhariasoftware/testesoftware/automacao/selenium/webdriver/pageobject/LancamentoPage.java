package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class LancamentoPage {

    private WebDriver driver;

    public LancamentoPage(final WebDriver driver) {
        this.driver = driver;

    }

    public int getQtdeInputs() {
        List<WebElement> inputs = driver.findElements(By.xpath("//input[contains(@type,'text')]"));
        List<WebElement> selects = driver.findElements(By.xpath("//select"));
        return inputs.size() + selects.size();
    }

    public int getQtdeMensagensCamposObrigatorios() {
        List<WebElement> msgCampos = driver.findElements(By.xpath("//div[contains(@class,'alert-danger')]/div/span"));
        return msgCampos.size();
    }

    public void salvar() {
        driver.findElement(By.id("btnSalvar")).click();
    }

    public void cancela() {
        driver.findElement(By.id("cancelar")).click();
    }

    public String edita() {
        WebElement descricao = driver.findElement(By.id("descricao"));
        String id = driver.findElement(By.id("id")).getAttribute("value");
        String descricaoAntiga = descricao.getAttribute("value");
        String novaDescricao = "editado <" + id + "> " + descricaoAntiga;
        descricao.click();
        descricao.clear();
        descricao.sendKeys(novaDescricao);
        this.salvar();
        return novaDescricao;
    }


    public void cria(Lancamento lancamento) {
        if (lancamento.getTipoLancamento() == TipoLancamento.SAIDA) {
            driver.findElement(By.id("tipoLancamento2")).click(); // informa lançamento: SAÍDA
        } else {
            driver.findElement(By.id("tipoLancamento1")).click(); // informa lançamento: ENTRADA
        }
        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.sendKeys(lancamento.getDescricao());
        driver.findElement(By.name("dataLancamento")).sendKeys(lancamento.getDataLancamentoFormatado());
        descricao.click();
        driver.findElement(By.id("valor")).sendKeys(lancamento.getValorFormatado());
        new Select(driver.findElement(By.id("categoria"))).selectByVisibleText(lancamento.getCategoria().getNome());
        this.salvar();
    }
}


