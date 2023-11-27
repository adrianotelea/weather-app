import axios from "axios";
import {searchState} from "../state/searchState.js";

const { searchResults } = searchState();

export const getWeatherAverages = (cities) => {
    axios.get(`http://localhost:8080/api/weather?city=${cities}`)
        .then(res => searchResults.value = res.data.result)
        .catch(e => {
            searchResults.value = [];
            if (e.code === 'ERR_NETWORK') {
                console.log(e.message);
            }
        });
}