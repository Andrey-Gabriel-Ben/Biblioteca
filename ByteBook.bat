@echo off
:: Garante que o console use UTF-8 para o João não virar Jo?o
chcp 65001 > nul

title ByteBook - Sistema de Gerenciamento

:: Executa o seu projeto
java -jar MeuSistema.jar

:: Impede que o CMD feche sozinho se o programa der erro
pause
