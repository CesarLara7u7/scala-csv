
# Test API CSV




## API Reference

#### Get Statistics for Number of employees hired for each job and department in 2021 divided by quarter.

```http
  GET /api/statistics
```

#### Send employees csv

```bash
  curl -v -F csv=@"rutaCSV/hired_employees.csv" /csv/employees
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `rutaCSV`      | `string` | CSV Route|


#### Send jobs csv

```bash
  curl -v -F csv=@"rutaCSV/jobs.csv" /csv/jobs
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `rutaCSV`      | `string` | CSV Route|


#### Send departments csv

```bash
  curl -v -F csv=@"rutaCSV/departments.csv" /csv/departments
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `rutaCSV`      | `string` | CSV Route|
