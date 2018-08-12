package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 Fluxo 4: Acessar listagem, criar novo lançamento, validar lançamento criado, validar
 totais de saída e entrada e acessar relatórios;
 */
public class Fluxo4 extends Fluxo1 {
    @Test(priority = 1)
    public void getTotalEntradaTest() {
        this.validarTotalEntrada();
    }

    @Test(priority = 2)
    public void getTotalSaidaTest() {
        this.validarTotalSaida();
    }

    @Test(priority = 3)
    void acessarRelatoriosTest() {
        this.acessarRelatorios();
    }
}
