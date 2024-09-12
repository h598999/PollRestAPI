<script>
  import {fetchPolls} from './apiClient'

  let polls = [];

  async function getPolls(){
    polls = await fetchPolls();
    console.log(polls);
  }
  getPolls();
  console.log("Hello World!")
</script>

<h1>Hello World!</h1>

<h2>
 {#if polls.length > 0}
  <ul>
    {#each polls as poll }
      <li>{poll.question}</li>
      {#each poll.voteOptions as option}
        <li> {option.caption} </li>
      {/each}
    {/each}
  </ul>
 {/if}
</h2>
