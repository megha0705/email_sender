async function fetchLogs() {
  try {
    const response = await fetch('/getAllFileLogs');
    const logs = await response.json();
    const tableBody = document.getElementById("logs-table");
    tableBody.innerHTML = "";

    logs.forEach(log => {
      const row = `<tr>
        <td>${log.id}</td>
        <td>${log.fileName}</td>
        <td>${log.scheduledTime || '-'}</td>
        <td class="status-${log.status}">${log.status}</td>

      </tr>`;
      tableBody.innerHTML += row;
    });
  } catch (error) {
    console.error("Error fetching logs:", error);
  }
}


fetchLogs();
setInterval(fetchLogs, 10000);
