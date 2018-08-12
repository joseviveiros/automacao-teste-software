package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 Fluxo 1: Acessar listagem, criar novo lançamento e validar lançamento criado;
 */
public class Fluxo1 extends LancamentoTest {
    @Test
    public void criarLancamentoTest() {
        this.criarLancamento();
    }
}
