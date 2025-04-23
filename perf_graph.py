'''
Inserted: 10000000 records using repository-saveAll in 2460578 ms
Inserted: 10000000 records using entity-manager in 2377019 ms
Inserted: 10000000 records using entity-manager-concurrent in 614553 ms
Inserted: 10000000 records using native-sql in 107833 ms
Inserted: 10000000 records using pl-sql in 122873 ms
Inserted: 10000000 records using csv-upload in 50547 ms

'''
import matplotlib.pyplot as plt

# Metrics data
methods = [
    "repository-saveAll",
    "entity-manager",
    "entity-manager-concurrent",
    "native-sql",
    "pl-sql",
    "csv-upload"
]

times_ms = [
    249286,
    237991,
    94958,
    9122,
    10606,
    3608
]

# Convert milliseconds to seconds for better readability
times_sec = [t / 1000 for t in times_ms]

# Plotting
plt.figure(figsize=(12, 6))
bars = plt.bar(methods, times_sec, color='skyblue')
plt.xlabel('Insertion Method')
plt.ylabel('Time Taken (seconds)')
plt.title('Time Taken to Insert 10,000,000 Records by Method')
plt.xticks(rotation=45)
plt.grid(axis='y', linestyle='--', alpha=0.7)

# Add labels on top of bars
for bar in bars:
    height = bar.get_height()
    plt.text(bar.get_x() + bar.get_width()/2, height + 10, f'{height:.1f}s', ha='center', va='bottom')

plt.tight_layout()
plt.show()
