import pandas as pd
import matplotlib.pyplot as plt
import os

times_folder = 'times'
csv_files = [f for f in os.listdir(times_folder) if f.endswith('.csv')]

plt.style.use('ggplot')

average_times_per_algorithm = {}
colors = ['b', 'g', 'r', 'c', 'm', 'y', 'k']  # Different colors for each algorithm
color_index = 0

for csv_file in csv_files:
    df = pd.read_csv(os.path.join(times_folder, csv_file))
    df.columns = df.columns.str.strip()
    
    if 'Time(ns)' in df.columns:
        df['Time(ms)'] = df['Time(ns)'] / 1_000_000
        algorithm_name = csv_file.replace('_times.csv', '').replace('_', ' ').title()

        avg_time_per_dataset = df.groupby('Dataset')['Time(ms)'].mean()
        overall_avg_time_ms = df['Time(ms)'].mean()

        average_times_per_algorithm[algorithm_name] = {
            'avg_times': avg_time_per_dataset,
            'overall_avg': overall_avg_time_ms,
            'color': colors[color_index % len(colors)]
        }
        color_index += 1

plt.figure(figsize=(14, 8))

for algorithm, data in average_times_per_algorithm.items():
    plt.plot(
        data['avg_times'].index, 
        data['avg_times'].values, 
        label=f"{algorithm} - Avg Time", 
        marker='o', 
        color=data['color']
    )

for algorithm, data in average_times_per_algorithm.items():
    plt.hlines(
        y=data['overall_avg'], 
        xmin=0, 
        xmax=len(data['avg_times']) - 1, 
        colors=data['color'], 
        linestyles='dashed', 
        label=f"{algorithm} - Overall Avg"
    )

plt.xlabel('Dataset')
plt.ylabel('Time (ms)')
plt.title('Performance of Sorting Algorithms (Avg Times per Dataset & Overall Avg)')
plt.xticks(rotation=45)
plt.legend(title='Algorithms', bbox_to_anchor=(1.05, 1), loc='upper left')

plt.tight_layout()
plt.show()
