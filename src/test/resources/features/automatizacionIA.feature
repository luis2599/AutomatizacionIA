#Validación lógica de decisión automatizada

Feature: Proceso de compras de productos en el catalogo 
  Como cliente de la tienda
  Quiero buscar productos por precio
  Para encontrar rápidamente artículos de mi interés

  #Esto es para ejecutar este paso al principio de cada escenario
  Background: Prerequisito del proceso de compra
    Given El usuario abre el navegador y navega al sitio web
    And El usuario ha iniciado sesion

  Scenario: Seleccion de productos segun criterio
    Given Selecciona el producto mas costoso 