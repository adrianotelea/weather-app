import {computed, signal} from "@preact/signals";

const searchClicked = signal(false);
const searchResults = signal([]);

const hasResults = computed(() => {
    return searchResults.value?.length > 0
});

export const searchState = () => {
    return { searchClicked, searchResults, hasResults};
};