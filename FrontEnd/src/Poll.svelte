<script>
import { getPollById, createVote } from './apiClient.js';

export let pollId;  // pollId is passed as a prop from App.svelte
export let currentUser;
let poll = null;
let voteoptions = [];

// Fetch poll details based on pollId
async function loadPoll() {
  poll = await getPollById(pollId);
}

/** 
 *@param{number} userid
 *@param{object} voteOption 
 **/
async function castVote(userid, voteOption){
  try{
    const voteData = {
      selected: voteOption,
    };
    const createdVote = await createVote(userid, voteData);
    console.log('Vote created successfully:', createdVote);
    getCastedVote();
  }catch (error){
    console.error('Error creating vote:', error);
  }
} 

function getCastedVote(){
  try{
    let castedVote = currentUser.votes;
    if (castedVote.length !== 0){
      voteoptions = [];
      castedVote.forEach((/** @type {{ selected: { id: Object; }; }} */ c) => {
          voteoptions.push(c.selected.id);
      });
    } else {
      console.log('There are no votes!');
    }
  } catch (error){
    console.log(error);
  }
}
getCastedVote();
$: loadPoll();  // Re-fetch the poll whenever pollId changes
</script>

<style>
  .container {
    display: flex;
    justify-content: center; /* Horizontally centers the table */
    align-items: center; /* Vertically centers the table */
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

  /* New class for green border */
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
            {#if voteoptions.includes(option.id) }
            <tr class={'voted-option'}>
            <td>{option.caption}<button on:click={() => castVote(currentUser.id, option)}>Vote</button></td>
            <td>{option.numberOfVotes} </td>
            </tr>
            {:else}
            <tr>
            <td>{option.caption}<button on:click={() => castVote(currentUser.id, option)}>Vote</button></td>
            <td>{option.numberOfVotes} </td>
            </tr>
            {/if}
        {/each}
      </tbody>
    </table>
  </div>
{:else}
  <p>Loading poll details...</p>
{/if}
