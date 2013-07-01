<h1>Calcolatrice matriciale razionale.</h1>
<h3>Operatori</h3>
<table>
	<tr>
		<th class="grid">Priorità</th>
		<th class="grid">Operatore</th>
		<th class="grid">Descrizione</th>
	</tr>
	<tr>
		<td class="grid">1</td>
		<td class="grid">( ... )</td>
		<td class="descr">Le parentesi danno priorità all'espressione tra
		parentesi.</td>
	</tr>
	<tr>
		<td class="grid">2</td>
		<td class="grid">+</td>
		<td class="descr">L'operatore positivo prima di un'espressione lascia
		inalterato il valore dell'espressione. E' inserito per simmetria
		all'operatore di negazione.</td>
	</tr>
	<tr>
		<td class="grid">2</td>
		<td class="grid">-</td>
		<td class="descr">L'operatore negazione prima di un'espressione lascia
		inverte il segno dell'espressione.</td>
	</tr>
	<tr>
		<td class="grid">2</td>
		<td class="grid">det(...)</td>
		<td class="descr">L'operatore determinante calcola il determinante
		dell'espressione tra parentesi.</td>
	</tr>
	<tr>
		<td class="grid">2</td>
		<td class="grid">Tr(...)</td>
		<td class="descr">L'operatore trasposta calcola la matrice trasposta
		dell'espressione tra parentesi.</td>
	</tr>
	<tr>
		<td class="grid">3</td>
		<td class="grid">*</td>
		<td class="descr">L'operatore di moltiplicazione calcola il prodotto
		tra la prima espressione e la seconda espressione.</td>
	</tr>
	<tr>
		<td class="grid">3</td>
		<td class="grid">/</td>
		<td class="descr">L'operatore di divisione calcola la divisione tra la
		prima espressione e la seconda espressione. Nel caso dei matrici
		esegue la moltiplicazine tra la prima espressione e la matrice inversa
		della seconda.</td>
	</tr>
	<tr>
		<td class="grid">4</td>
		<td class="grid">+</td>
		<td class="descr">L'operatore di somma calcola la somma tra la prima
		espressione e la seconda espressione.</td>
	</tr>
	<tr>
		<td class="grid">4</td>
		<td class="grid">-</td>
		<td class="descr">L'operatore di sottrazione calcola la differenza tra
		la prima espressione e la seconda espressione.</td>
	</tr>
</table>