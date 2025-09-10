document.addEventListener("DOMContentLoaded", async () => {
    console.log("JS loaded");

    const container = document.getElementById("logs-list");
    container.innerHTML = "";

    try {
        const response = await fetch("/logs");
        console.log(response);

        if (!response.ok) throw new Error("Failed to fetch logs");

        const logs = await response.json();
        console.log(logs);

        if (logs.length === 0) {
            container.innerHTML = "<p>No logs yet.</p>";
            return;
        }


        const table = document.createElement("table");
        table.className = "logs-table";


        table.innerHTML = `
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Time</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Error</th>
                </tr>
            </thead>
            <tbody></tbody>
        `;

        const tbody = table.querySelector("tbody");


        logs.forEach(l => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${l.id}</td>
                <td>${l.logTime}</td>
                <td>${l.emailId}</td>
                <td class="status ${l.status.toLowerCase()}">${l.status}</td>
                <td>${l.errorMsg || "-"}</td>
            `;
            tbody.appendChild(row);
        });

        container.appendChild(table);

    } catch (error) {
        container.innerHTML = `<p style="color:red;">Error loading logs: ${error.message}</p>`;
        console.error(error);
    }
});
