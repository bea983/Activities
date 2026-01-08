// Wait for the DOM to load
document.addEventListener('DOMContentLoaded', function() {
    const usernameDisplay = document.getElementById('usernameDisplay');
    const dateDisplay = document.getElementById('dateDisplay');
    const taskInput = document.getElementById('taskInput');
    const addTaskBtn = document.getElementById('addTaskBtn');
    const taskList = document.getElementById('taskList');

    // Set username (prompt if not set, store in localStorage)
    let username = localStorage.getItem('username');
    if (!username) {
        username = prompt('Enter your username:') || 'Anonymous';
        localStorage.setItem('username', username);
    }
    usernameDisplay.textContent = `Welcome, ${username}!`;

    // Set current date
    const today = new Date().toLocaleDateString('en-US', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
    dateDisplay.textContent = today;

    // Load tasks from localStorage
    loadTasks();

    // Add task on button click
    addTaskBtn.addEventListener('click', addTask);

    // Add task on Enter key press
    taskInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            addTask();
        }
    });

    function addTask() {
        const taskText = taskInput.value.trim();
        if (taskText === '') {
            alert('Please enter a task!');
            return;
        }

        // Create new list item
        const li = document.createElement('li');
        li.innerHTML = `
            <span class="task-text">${taskText}</span>
            <div class="button-group">
                <button class="edit-btn">Edit</button>
                <button class="save-btn" style="display: none;">Save</button>
                <button class="delete-btn">Delete</button>
            </div>
        `;

        // Toggle completion on click (target the span)
        li.querySelector('.task-text').addEventListener('click', function() {
            li.classList.toggle('completed');
            saveTasks();
        });

        // Add edit button event listener
        li.querySelector('.edit-btn').addEventListener('click', function() {
            const taskSpan = li.querySelector('.task-text');
            const currentText = taskSpan.textContent;
            const input = document.createElement('input');
            input.type = 'text';
            input.value = currentText;
            input.className = 'edit-input';
            
            // Replace the span with the input
            taskSpan.replaceWith(input);
            input.focus();

            // Handle saving the edit
            function saveEdit() {
                const newText = input.value.trim();
                if (newText !== '') {
                    const newSpan = document.createElement('span');
                    newSpan.className = 'task-text';
                    newSpan.textContent = newText;
                    input.replaceWith(newSpan);
                    
                    // Reattach the click event for completion toggle
                    newSpan.addEventListener('click', function() {
                        li.classList.toggle('completed');
                        saveTasks();
                    });
                    
                    saveTasks();
                }
            }

            // Save on Enter key
            input.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    saveEdit();
                }
            });

            // Save on blur (when input loses focus)
            input.addEventListener('blur', saveEdit);
        });

        // Add delete button event listener
        li.querySelector('.delete-btn').addEventListener('click', function() {
            if (confirm('Are you sure you want to delete this task?')) {
                li.remove();
                saveTasks();
            }
        });

        taskList.appendChild(li);
        taskInput.value = ''; // Clear input
        saveTasks(); // Save to localStorage
    }



    function saveTasks() {
        const tasks = [];
        taskList.querySelectorAll('li').forEach(li => {
            const taskText = li.querySelector('.task-text').textContent;
            const isCompleted = li.classList.contains('completed');
            tasks.push({ text: taskText, completed: isCompleted });
        });
        localStorage.setItem('tasks', JSON.stringify(tasks));
    }

    function loadTasks() {
        const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
        tasks.forEach(task => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span class="task-text">${task.text}</span>
                <div>
                    <button class="edit-btn">Edit</button>
                    <button class="delete-btn">Delete</button>
                </div>
            `;
            if (task.completed) {
                li.classList.add('completed');
            }
            li.querySelector('.task-text').addEventListener('click', function() {
                li.classList.toggle('completed');
                saveTasks();
            });

            // Add edit button event listener
            li.querySelector('.edit-btn').addEventListener('click', function() {
                const taskSpan = li.querySelector('.task-text');
                const currentText = taskSpan.textContent;
                const input = document.createElement('input');
                input.type = 'text';
                input.value = currentText;
                input.className = 'edit-input';
                
                // Replace the span with the input
                taskSpan.replaceWith(input);
                input.focus();

                // Show save button and hide edit button
                const editBtn = li.querySelector('.edit-btn');
                const saveBtn = li.querySelector('.save-btn');
                editBtn.style.display = 'none';
                saveBtn.style.display = 'inline-block';

                // Handle saving the edit
                function saveEdit() {
                    const newText = input.value.trim();
                    if (newText !== '') {
                        const newSpan = document.createElement('span');
                        newSpan.className = 'task-text';
                        newSpan.textContent = newText;
                        input.replaceWith(newSpan);
                        
                        // Reattach the click event for completion toggle
                        newSpan.addEventListener('click', function() {
                            li.classList.toggle('completed');
                            saveTasks();
                        });
                        
                        // Show edit button and hide save button
                        editBtn.style.display = 'inline-block';
                        saveBtn.style.display = 'none';
                        
                        saveTasks();
                    }
                }

                // Add save button event listener
                saveBtn.addEventListener('click', saveEdit);

                // Save on Enter key
                input.addEventListener('keypress', function(e) {
                    if (e.key === 'Enter') {
                        saveEdit();
                    }
                });
            });

            // Add delete button event listener
            li.querySelector('.delete-btn').addEventListener('click', function() {
                if (confirm('Are you sure you want to delete this task?')) {
                    li.remove();
                    saveTasks();
                }
            });
            
            taskList.appendChild(li);
        });
    }
});
