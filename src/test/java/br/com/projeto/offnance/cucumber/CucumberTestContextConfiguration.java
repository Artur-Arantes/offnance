package br.com.projeto.offnance.cucumber;

import br.com.projeto.offnance.OffnanceAplpicationApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = OffnanceAplpicationApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
