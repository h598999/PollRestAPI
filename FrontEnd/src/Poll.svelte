<script>
import { getPollById, createVote } from './apiClient.js';

export let pollId;  // pollId is passed as a prop from App.svelte
export let currentUser;
console.log(currentUser);
let poll = null;

// Fetch poll details based on pollId
async function loadPoll() {
  poll = await getPollById(pollId);
}

/** @param{object} voteOption **/
async function castVote(voteOption){
  try{
    const voteData = {
      selected: voteOption,
    };
    const createdVote = await createVote(voteData);
    console.log('Vote created successfully:', createdVote);
  }catch (error){
    console.error('Error creating vote:', error);
  }
} 
$: loadPoll();  // Re-fetch the poll whenever pollId changes
</script>

<style>
  .container {
    display: flex;
    justify-content: center; /* Horizontally centers the table */
    align-items: center; /* Vertically centers the table */
    /* height: 100vh; /* Full viewport height */
    text-align: center; /* Optional: centers text within table cells */
  }

  table {
    border-collapse: collapse; /* Optional: collapses borders for cleaner look */
    margin: auto; /* Optional: centers the table horizontally */
  }

  td, th {
    padding: 8px; /* Optional: adds padding to table cells */
    border: 1px solid #ddd; /* Optional: adds border to table cells */
  }
</style>
{#if poll}
<h2>Q: {poll.question}</h2>
  <div class="container">
    <table>
      <thead>
        <tr>
          <th>Options:</th>
        </tr>
      </thead>
      <tbody>
        {#each poll.voteOptions as option}
          <tr>
            <td>{option.caption} <button on:click={() => castVote(option)}>Vote</button></td>
            <td>Current no. of votes: {option.numberOfVotes} </td>
          </tr>
        {/each}
      </tbody>
    </table>
  </div>
{:else}
  <p>Loading poll details...</p>
{/if}

