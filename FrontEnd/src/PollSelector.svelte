<script>
  import { createEventDispatcher } from 'svelte';  // Import Svelte's event dispatcher
  import { fetchPolls } from './apiClient';
  export let currentUser;
  console.log(currentUser);
  let polls = [];
  const dispatch = createEventDispatcher();  // Create an event dispatcher

  async function getPolls() {
    polls = await fetchPolls();
    console.log(polls);
  }

  /** @param{number} id **/
  function selectPoll(id) {
    dispatch('selectPoll', id);  // Use the dispatcher to send the selected poll ID
  }

  getPolls();
</script>

<h1>Welcome to the Poll app!</h1>
<br>
<h4>
  {#if polls.length > 0}
    <ul>
      {#each polls as poll }
        <li>{poll.id + 1} {poll.question} <button on:click={() => selectPoll(poll.id)}> Vote </button></li>
      {/each}
    </ul>
  {/if}
</h4>

