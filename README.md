# EnergyCalc 🌍⚡

## **Descrição do Projeto**

EnergyCalc é uma aplicação voltada para simulações de consumo de energia com foco em sustentabilidade. O sistema permite que os usuários calculem e comparem os custos e benefícios de diferentes tipos de energia, como solar, eólica e hídrica. Além disso, fornece um histórico das simulações realizadas, ajudando os usuários a tomarem decisões baseadas em dados reais.

---

## **Links**

- **Apresentação do projeto** 
- **Repositório do front:** https://github.com/TCapela/GS2---FRONT.git
- **Apresentação do projeto:** 
- **Repositorio java:** https://github.com/raphatatto/gs-java.git
- **Vídeo Pitch:**

## **Objetivo do Projeto**

- Fornecer uma ferramenta intuitiva e acessível para simular diferentes tipos de energia.
- Aumentar a conscientização sobre a importância do uso de energia renovável.
- Auxiliar na tomada de decisão para reduzir custos energéticos.

---

## **Equipe de Desenvolvimento**

| Nome                       | RM       | Turma  |
|----------------------------|----------|--------|
| Raphaela Oliveira Tatto    | RM554983 | 1TDSPO |
| Lucas Rodrigues de Souza   | RM557951 | 1TDSPO |
| Tiago Ribeiro Capela       | RM558021 | 1TDSPO |

---

## **Tecnologias Utilizadas**

### **Frontend**
- **React.js** com **Next.js** ⚛️
- **TypeScript** para tipagem estática 🛠️
- **Tailwind CSS** para estilização responsiva 🎨

### **Backend**
- **Java** com **Jakarta EE** ☕
- **JPA (Java Persistence API)** para interação com o banco de dados 📦
- **H2 Database** para desenvolvimento local e **OracleDatabase** para produção 🗄️
- **API RESTful** com suporte a CORS

### **Bibliotecas Adicionais**
- **Vercel** para deploy contínuo 🚀
- **MARP** para apresentação dinâmica de documentação 📄
- **Postman** para testes de API 🔧

---

## **Funcionalidades**

1. **Simulação de Consumo**
   - Permite calcular o consumo e os custos com base nos dados fornecidos pelo usuário.

2. **Histórico de Simulações**
   - Exibe uma lista de simulações realizadas pelo usuário.

3. **Login e Registro**
   - Controle de acesso seguro.

4. **Mensagens Dinâmicas**
   - Exibe mensagens claras sem o uso de `alert()`.

5. **Modal de Exclusão**
   - Confirma exclusões de forma intuitiva e amigável.

6. **Modal de Edição**
   - Edita as informações das simulações.

7. **Página para ver a simulação**
   - Utilização de rotas dinâmicas
---

# Estrutura do Projeto

Este documento descreve a estrutura do projeto, organizando os pacotes e suas respectivas responsabilidades.

## Pacotes

### `br.com.fiap.bo`
Contém as classes de lógica de negócios (Business Objects) que gerenciam as regras da aplicação:
- `ConsumoBO`
- `SimulacaoBO`

### `br.com.fiap.dao`
Contém as classes de acesso a dados (Data Access Objects), responsáveis por interagir com o banco de dados:
- `ConsumoDAO`
- `EnergiaRenovavelDAO`
- `SimulacaoDAO`
- `UsuarioDAO`

### `br.com.fiap.exception`
Define classes de exceções personalizadas para lidar com erros específicos do sistema:
- `CustomException`

### `br.com.fiap.filter`
Contém filtros usados na aplicação, como configuração de CORS:
- `CorsFilter`

### `br.com.fiap.model`
Define os modelos de dados que representam as entidades do sistema:
- `Consumo`
- `EnergiaRenovavel`
- `Simulacao`
- `Usuario`

### `br.com.fiap.resource`
Implementa os endpoints RESTful para interação com as funcionalidades da aplicação:
- `AuthResource`
- `ConsumoResource`
- `EnergiaRenovavelResource`
- `SimulacaoResource`
- `UsuarioResource`

### `br.com.fiap.util`
Contém utilitários auxiliares para o funcionamento da aplicação:
- `ConnectionFactory`: Classe para gerenciar conexões com o banco de dados.

### `org.example`
Contém a classe principal para inicializar a aplicação:
- `Main`

## Outras Pastas

## Dependências
As dependências e plugins utilizados no projeto estão especificados no arquivo `pom.xml`.

## Observações
- A aplicação segue o padrão de arquitetura RESTful.
- Os filtros implementados garantem que o sistema seja acessível de diferentes origens (CORS).

## **Como Executar o Projeto**

### **Requisitos**

- Node.js versão 16+ 🔧
- Java 17+ ☕
- Oracle Database 🎲
- Navegador atualizado 🌐

### **Passos para Instalação**

### **IMPORTANTE:**
**Há uma chance de ao fazer o clone o programa não funcionar corretamente devido ao plugin do Lombok que por algum motivo ao ser enviado ao Github e depois ser clonado o programa não reconhece mais o Lombok, caso isso ocorra instale o zip do projeto no mesmo local onde você consegue a URL.**

**Ferramenta:**
Recomenda-se o uso do IntelliJ pois ele pode fornecer todas as funcionalidade e dependências necessárias sem chances de haver algum erro grave.

**Dependências:**
No IntelliJ tenha certeza de ter instalado a extensão do Lombok pois ela é fundamental para o funcionamento do sistema.

**"Onde Baixar?"**
Vá em File > Settings > Plugins

Então clique em MarketPlace e pesquise "Lombok"

Instale o plugin e clique em "apply" e depois em "ok"

#### **1. Clone o repositório**
```bash
git clone https://github.com/TCapela/GS2---FRONT.git
cd global-solution
cd energy-calc
npm i
npm run dev
```


