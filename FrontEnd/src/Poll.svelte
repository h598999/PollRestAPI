<script>
import { onMount } from 'svelte';
import { getPollById, createVote, updateVote } from './apiClient.js';
import Tables from './Table.svelte';

export let pollId;
export let currentUser;
export let poll = null;
export let voteoptions = [];
export let currentVote = findVoteForPoll();

// Fetch poll details based on pollId
async function loadPoll() {
  poll = await getPollById(pollId);
  getCastedVote(); // Fetch user's existing votes after poll data is loaded
  console.log(currentVote);
  console.log('Poll updated after vote!');
}

// Run loadPoll when component is first mounted (initial rendering)
onMount(() => {
  loadPoll();
});

async function handleVote(event) {
  await castVote(currentUser.id, event.detail.option);
  // Re-fetch the poll after the vote is cast
  await loadPoll(); // This updates the poll after voting
}

/** 
 *@param {number} userid
 *@param {object} voteOption 
 **/
async function castVote(userid, voteOption) {
  try {
    const voteData = {
      selected: voteOption,
    };
    if (voteoptions.length != 0) {
      let vote = findVoteForPoll();
      await updateVote(vote.id, voteData);
      console.log('Vote updated successfully');
    } else {
      await createVote(userid, voteData);
      console.log('Vote created successfully');
    }
    currentVote = voteData;
  } catch (error) {
    console.error('Error creating vote:', error);
  }
}

function findVoteForPoll() {
  let voted = null;
  try {
    getCastedVote();
    currentUser.votes.forEach((/** @type {Object} */ element) => {
      if (element.selected.id == voteoptions[0]) {
        voted = element;
      }
    });
  } catch (error) {
    console.log(error);
  }
  return voted;
}

currentVote = findVoteForPoll();
function getCastedVote() {
  try {
    let castedVotes = currentUser.votes;
    if (castedVotes.length !== 0) {
      voteoptions = castedVotes.map(c => c.selected.id); // Create a list of voted option ids
    } else {
      voteoptions = []; // Clear the list if there are no votes
    }
  } catch (error) {
    console.log(error);
  }
}
</script>

{#key currentVote}
<Tables {poll} {currentVote} on:vote-option={handleVote} />
{/key}

