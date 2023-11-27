import {searchState} from "../state/searchState.js";
import styles from './results.module.css';

const { searchResults } = searchState();

const Results = () => {
    return (
        <div className={styles.resultsContainer}>
            {searchResults?.value?.map(res => (
                <div className={styles.resultCard}>
                    <p>{res.name}</p>
                    <span>Temperature: {res.temperature}</span>
                    <span>Wind: {res.wind}</span>
                </div>
                )
            )}
        </div>
    )
};

export default Results;