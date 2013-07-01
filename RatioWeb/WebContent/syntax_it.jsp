<h1>Calcolatrice matriciale razionale.</h1>
<h3>Sintassi</h3>
<p>La sintassi dei comandi nel formato BNF è:</p>
<p>commando := &lt;nome-variabile&gt; = &lt;espressione&gt;<br />
<br />
nome-variabile := &lt;inizio-variabile&gt; [&lt;fine-variabile&gt;]<br />
inizio-variabile := 'a'-'z' | 'A'-'Z' | '_' <br />
fine-variabile := &lt;inizio-variabile&gt;
[&lt;carattere-di-variabile&gt;] <br />
carattere-di-variabile := &lt;inizio-variabile&gt; | '0'-'9'<br />
<br />
espressione := &lt;espressione-prodotto&gt; [ { '+' | '-' }
&lt;espressione&gt;]<br />
<br />
espressione-prodotto := &lt;espressione-unaria&gt; [ { '*' | '/' }
&lt;espressione-prodotto&gt;]<br />
<br />
espressione-unaria := &lt;nome-variabile&gt;<br />
espressione-unaria := &lt;costante&gt;<br />
espressione-unaria := { '+' | '-'} &lt;espressione-unaria&gt;<br />
espressione-unaria := '(' &lt;espressione&gt; ')'<br />
espressione-unaria := det( &lt;espressione&gt; ')'<br />
espressione-unaria := Tr( &lt;espressione&gt; ')'<br />
</p>