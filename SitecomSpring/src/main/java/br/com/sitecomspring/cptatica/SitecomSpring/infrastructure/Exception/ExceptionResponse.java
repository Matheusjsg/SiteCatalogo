package br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Exception;

import java.util.Date;

public record ExceptionResponse(Date timestemp, String mensagem, String detalhes) {}
