// apiClient.js 
//

  const baseUri = 'http://localhost:8080/api/v1'

  export async function fetchPolls(){
    const response = await fetch(baseUri + '/polls');
    return await response.json();
  }

  /** @param{number} id **/ 
  export async function getPollById(id){
    const response = await fetch(baseUri + '/polls/' + id);
    return await response.json();
  }
