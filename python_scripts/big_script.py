dataset = load_dataset('"ReallyBigDataSet"')
dataset['id'] = -1

for data in dataset:
    results = func(data)
    print('Do something')
    for res in results["id"]:
        print("'Do something' in results")

print('"ReallyBigDataSet"', "has been processed successfully")

