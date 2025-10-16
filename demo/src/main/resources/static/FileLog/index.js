async function fetchLogs() {
  try {
    const response = await fetch('/getAllFileLogs');
    const logs = await response.json();
    const tableBody = document.getElementById("logs-table");
    tableBody.innerHTML = "";

    logs.forEach(log => {
      const row = `<tr>
        <td>${log.id}</td>
         <td>
          <a href="FileEmail.html?fileId=${log.id}" class="file-link">
            ${log.fileName}
          </a>
        </td>
        <td>${log.scheduledTime || '-'}</td>
        <td class="status-${log.status}">${log.status}</td>

         <td>
            <button class="reschedule-btn" onclick="reschedule(${log.id})">Reschedule</button>
          </td>
      </tr>`;
      tableBody.innerHTML += row;
    });
  } catch (error) {
    console.error("Error fetching logs:", error);
  }
}
function reschedule(fileId) {

  window.location.href = `../createCampaign/createCampaign.html?fileId=${fileId}`;
}

fetchLogs();
setInterval(fetchLogs, 10000);
