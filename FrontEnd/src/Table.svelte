<script>
import { createEventDispatcher } from 'svelte';  // Import Svelte's event dispatcher
const dispatch = createEventDispatcher();
export let poll;
export let currentVote;

if (currentVote != null){
  console.log('From table: ' + currentVote.selected.id);
}

/** @param{Object} option**/
function vote(option) {
  dispatch('vote-option', { option: option });
}
</script>

<style>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
  }

  table {
    border-collapse: collapse;
    margin: auto;
  }

  td, th {
    padding: 8px;
    border: 1px solid #ddd;
  }

  .voted-option {
    border: 2px solid green;
  }
</style>

{#if poll}
  <h2>Q: {poll.question}</h2>
  <div class="container">
    <table>
      <thead>
        <tr>
          <th>Options:</th>
          <th>Number of votes:</th>
        </tr>
      </thead>
      <tbody>
        {#each poll.voteOptions as option}
          {#if currentVote!=null && currentVote.selected.id == (option.id)}
          <tr class="voted-option">
            <td>{option.caption}</td>
            <td>{option.numberOfVotes}</td>
          </tr>
          {:else}
          <tr>
            <td>{option.caption}<button on:click={() => vote(option)}>Vote</button></td>
            <td>{option.numberOfVotes}</td>
          </tr>
          {/if}
        {/each}
      </tbody>
    </table>
  </div>
{:else}
  <p>Loading poll details...</p>
{/if}

