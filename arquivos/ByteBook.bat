@echo off
chcp 65001 > nul
title ByteBook - Sistema de Gerenciamento

:: Move o foco do terminal para a pasta onde o ficheiro .bat está
cd /d "%~dp0"

:: Compila tudo novamente para garantir que as versões estão certas
javac -cp ".;postgresql-42.7.10.jar" *.java

:: Executa a classe principal (sem o .java no fim)
java -cp ".;postgresql-42.7.10.jar" ByteBook

pause