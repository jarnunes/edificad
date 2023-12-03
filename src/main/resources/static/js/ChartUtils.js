// https://chartjs-plugin-datalabels.netlify.app/guide/options.html#scriptable-options
// https://www.chartjs.org/docs/latest/charts/bar.html

Chart.register(ChartDataLabels)

class ChartUtils {

    static jsonToLineChartData(json) {
        return {
            labels: json.labels,
            datasets: [{
                data: json.data,
                borderColor: ['rgb(69,140,234)'],
                borderWidth: 1
            }]
        };
    }

    static jsonToBarChartData(json) {
        return {
            labels: json.labels,
            datasets: [{
                data: json.data,
                backgroundColor: json.background_colors,
                borderColor: json.border_colors,
                borderWidth: 0
            }]
        };
    }

    static jsonToPieChartData(json) {
        return {
            labels: json.labels,
            datasets: [{
                data: json.data,
                backgroundColor: json.background_colors,
                borderColor: json.border_colors,
                borderWidth: 0
            }]
        };
    }

    static getBarChartOptions(displayTitle, title) {
        return {
            layout: {
                padding: {
                    top: 30
                }
            },
            scales: {
                y: {
                    display: false,
                    grid: {
                        display: false
                    },

                },
                x: {
                    grid: {
                        display: false
                    }
                }
            },
            plugins: {
                title: {
                    display: displayTitle,
                    text: title
                },
                subtitle: {
                    display: false
                },
                legend: {
                    display: false
                },
                datalabels: {
                    anchor: 'end',
                    align: 'top'
                }
            }
        }
    }

    static getPieChartOptions(displayTitle, title) {
        return {
            plugins: {
                title: {
                    display: displayTitle,
                    text: title
                },
                subtitle: {
                    display: false
                },
                legend: {
                    display: true,
                    position: 'bottom'
                },
                datalabels: {
                    anchor: 'end',
                    align: 'top'
                }
            }
        }
    }

    static getLineChartOptions(displayTitle, title) {
        return {
            scales: {
                y: {
                    display: false,
                    grid: {
                        display: false
                    },

                },
                x: {
                    grid: {
                        display: false
                    }
                }
            },
            plugins: {
                title: {
                    display: displayTitle,
                    text: title
                },
                subtitle: {
                    display: false
                },
                legend: {
                    display: false
                },
                datalabels: {
                    anchor: 'end',
                    align: 'top'
                }
            }
        }
    }


}
