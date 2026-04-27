@echo off
:: Garante que o console use UTF-8
chcp 65001 > nul

:: TÍTULO DA JANELA
title ByteBook - Sistema de Gerenciamento

:: O comando abaixo une a biblioteca do Postgres ao seu código
:: O "." significa "procure nesta pasta" e o ";" separa os arquivos
java -cp ".;postgresql-42.7.10.jar" ByteBook.java


:: Impede que o CMD feche sozinho
pause