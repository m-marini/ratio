<h1>Rational Calculator.</h1>
<h3>Syntax</h3>
<p>The command syntax, in BNF format, is:</p>
<p>comand := &lt;variabile-name&gt; '=' &lt;expression&gt;<br />
<br />
variabile-name := &lt;variabile-begin&gt; [&lt;variabile-end&gt;]<br />
variabile-begin := 'a'-'z' | 'A'-'Z' | '_' <br />
variabile-end := &lt;variabile-begin&gt; [&lt;character-variabile&gt;] <br />
character-variabile := &lt;variabile-begin&gt; | '0'-'9'<br />
<br />
expression := &lt;multiply-expression&gt; [ { '+' | '-' }
&lt;expression&gt;]<br />
<br />
multiply-expression := &lt;unary-expression&gt; [ { '*' | '/' }
&lt;multiply-expression&gt;]<br />
<br />
unary-expression := &lt;variabile-name&gt;<br />
unary-expression := &lt;costant&gt;<br />
unary-expression := { '+' | '-'} &lt;unary-expression&gt;<br />
unary-expression := '(' &lt;expression&gt; ')'<br />
unary-expression := det( &lt;expression&gt; ')'<br />
unary-expression := Tr( &lt;expression&gt; ')'<br />
</p>
