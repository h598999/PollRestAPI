<script>
  import { createPoll } from './apiClient.js'
  export let currentUser;
  let pollQuestion = '';
  let voteOptions = [];
  let newOption = '';

  // Function to add a new vote option
  function addOption() {
    if (newOption.trim() !== '') {
      let createdOption = {
          caption: newOption,
          validUntil: "2024-12-31T23:59:59Z",
        }
      voteOptions = [...voteOptions, createdOption];
      newOption = ''; // Clear input after adding
    }
  }

  /** @param{number} index **/
  function removeOption(index){
    voteOptions = voteOptions.filter((_, i) => i !== index);
    }

  // Function to submit the poll (you can modify this to handle actual backend logic)
  async function postPoll() {
    if (pollQuestion.trim() !== '' && voteOptions.length >= 2) {
      let pollstruct = {
          question: pollQuestion,
          voteOptions: voteOptions,
        }
      let response = await createPoll(currentUser.id,pollstruct)
      pollQuestion = '';
      voteOptions = [];
    } else {
      console.log('Please enter a poll question and at least one vote option');
    }
  }
</script>

<h1>Create a new Poll!</h1>

<div>
  <!-- Poll Question Input -->
  <label for="question">Poll Question:</label>
  <input type="text" id="question" bind:value={pollQuestion} placeholder="Enter your poll question" />

  <!-- List of Vote Options -->
  <h3>Vote Options:</h3>
  {#if voteOptions.length === 0}
    <p>No options added yet.</p>
  {/if}
  <ul>
    {#each voteOptions as option, index}
      <li>
        {option.caption} <button on:click={() => removeOption(index)}>Remove</button>
      </li>
    {/each}
  </ul>

  <!-- Add New Option -->
  <input type="text" bind:value={newOption} placeholder="Add a vote option" />
  <button on:click={addOption}>Add Option</button>

  <!-- Submit Poll -->
  <button on:click={() => postPoll()}>Create Poll</button>
</div>

