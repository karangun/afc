<%@ page import="afc.Match" %>


<div class="fieldcontain ${hasErrors(bean: matchInstance, field: 'date', 'error')} ">
	<label for="date">
		<g:message code="match.date.label" default="Date" />
		
	</label>
	<joda:dateTimePicker name="date" value="${matchInstance?.date ?: new org.joda.time.LocalDateTime()}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: matchInstance, field: 'home', 'error')} ">
	<label for="home">
		<g:message code="match.home.label" default="Home" />
		
	</label>
	<g:select id="home" name="home.id" from="${afc.Team.list()}" optionKey="id" value="${matchInstance?.home?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: matchInstance, field: 'away', 'error')} ">
	<label for="away">
		<g:message code="match.away.label" default="Away" />
		
	</label>
	<g:select id="away" name="away.id" from="${afc.Team.list()}" optionKey="id" value="${matchInstance?.away?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: matchInstance, field: 'place', 'error')} ">
	<label for="place">
		<g:message code="match.place.label" default="Place" />
		
	</label>
	<g:textField name="place" value="${matchInstance?.place}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: matchInstance, field: 'commentators', 'error')} ">
	<label for="place">
		<g:message code="match.commentators.label" default="Commentators" />
		
	</label>
	
	<g:select name="commentators"
          from="${afc.Commentator.list()}"
          value="${matchInstance?.commentators?.commentator?.id}"
          optionKey="id"
          multiple="true" />
	

</div>

