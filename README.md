# Circuito-fisica

Problema 71

Este projeto consiste em uma aplicação desktop desenvolvida em **Java (Swing)** criada para resolver e simular numericamente o **Problema 71**.

---

## 📋 Resumo do Problema

O problema apresenta um circuito elétrico malhado complexo alimentado por uma fonte ideal de força eletromotriz (fem) $\mathcal{E}$ e composto por resistores de valores $R_1$ e $R_2$. O circuito possui três chaves ($S_1$, $S_2$ e $S_3$) que podem ser abertas ou fechadas de forma independente. 

O objetivo principal é determinar a **corrente elétrica no ponto $a$** (saída imediata da fonte) sob três condições distintas:
1. **Caso (a):** Apenas a chave $S_1$ está fechada.
2. **Caso (b):** Apenas as chaves $S_1$ e $S_2$ estão fechadas.
3. **Caso (c):** As três chaves ($S_1$, $S_2$ e $S_3$) estão fechadas.

---

O programa automatiza os cálculos algébricos que manualmente exigiriam a resolução de sistemas lineares complexos à medida que mais chaves são acionadas.

## ⚠️ Tratamento de Erros e Validação:

* **Entrada Não-Numérica (`NumberFormatException`):** Captura erros caso o usuário digite letras, símbolos ou deixe o campo vazio. O programa impede o cálculo e avisa qual campo está incorreto.
* **Valores Negativos ou Nulos:** Numericamente, resistências nulas gerariam divisões por zero ($\infty$). Fisicamente, não existem resistores ou fontes negativas neste contexto. O limite mínimo aceito é de `0.001`.
* **Limite Máximo de Segurança:** Para evitar estouro de ponto flutuante ou valores incoerentes com o problema proposto, foi adotado um teto de limite máximo razoável de `1000.0` para todas as grandezas ($V$ ou $\Omega$).

> 💡 **Feedback Visual:** Sempre que um erro é disparado, a interface limpa os resultados anteriores e exibe uma mensagem explicativa na cor **vermelha**. Quando o cálculo é bem-sucedido, uma mensagem **verde** confirma a operação.
