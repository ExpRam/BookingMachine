const baseFormatterOptions: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
};

export const baseFormatter = new Intl.DateTimeFormat('ru-RU', baseFormatterOptions);

export const formatterWithTime = new Intl.DateTimeFormat('ru-RU', {
    ...baseFormatterOptions,
    hour: '2-digit',
    minute: '2-digit',
});
