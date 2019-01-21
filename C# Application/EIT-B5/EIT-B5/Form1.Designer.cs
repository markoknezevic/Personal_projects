namespace EIT_B5
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.unosToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.unosToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.periodiToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.lokalitetiToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.poArhiologuToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.poTipuAkvititetaToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.izlazToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.izlazAltIToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.unosToolStripMenuItem,
            this.lokalitetiToolStripMenuItem,
            this.izlazToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(284, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // unosToolStripMenuItem
            // 
            this.unosToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.unosToolStripMenuItem1,
            this.periodiToolStripMenuItem});
            this.unosToolStripMenuItem.Name = "unosToolStripMenuItem";
            this.unosToolStripMenuItem.Size = new System.Drawing.Size(46, 20);
            this.unosToolStripMenuItem.Text = "Unos";
            // 
            // unosToolStripMenuItem1
            // 
            this.unosToolStripMenuItem1.Name = "unosToolStripMenuItem1";
            this.unosToolStripMenuItem1.Size = new System.Drawing.Size(180, 22);
            this.unosToolStripMenuItem1.Text = "Tip antikviteta";
            this.unosToolStripMenuItem1.Click += new System.EventHandler(this.unosToolStripMenuItem1_Click);
            // 
            // periodiToolStripMenuItem
            // 
            this.periodiToolStripMenuItem.Name = "periodiToolStripMenuItem";
            this.periodiToolStripMenuItem.Size = new System.Drawing.Size(180, 22);
            this.periodiToolStripMenuItem.Text = "Periodi";
            this.periodiToolStripMenuItem.Click += new System.EventHandler(this.periodiToolStripMenuItem_Click);
            // 
            // lokalitetiToolStripMenuItem
            // 
            this.lokalitetiToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.poArhiologuToolStripMenuItem,
            this.poTipuAkvititetaToolStripMenuItem});
            this.lokalitetiToolStripMenuItem.Name = "lokalitetiToolStripMenuItem";
            this.lokalitetiToolStripMenuItem.Size = new System.Drawing.Size(67, 20);
            this.lokalitetiToolStripMenuItem.Text = "Lokaliteti";
            // 
            // poArhiologuToolStripMenuItem
            // 
            this.poArhiologuToolStripMenuItem.Name = "poArhiologuToolStripMenuItem";
            this.poArhiologuToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.poArhiologuToolStripMenuItem.Text = "Po arhiologu";
            this.poArhiologuToolStripMenuItem.Click += new System.EventHandler(this.poArhiologuToolStripMenuItem_Click);
            // 
            // poTipuAkvititetaToolStripMenuItem
            // 
            this.poTipuAkvititetaToolStripMenuItem.Name = "poTipuAkvititetaToolStripMenuItem";
            this.poTipuAkvititetaToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.poTipuAkvititetaToolStripMenuItem.Text = "Po tipu akvititeta";
            this.poTipuAkvititetaToolStripMenuItem.Click += new System.EventHandler(this.poTipuAkvititetaToolStripMenuItem_Click);
            // 
            // izlazToolStripMenuItem
            // 
            this.izlazToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.izlazAltIToolStripMenuItem});
            this.izlazToolStripMenuItem.Name = "izlazToolStripMenuItem";
            this.izlazToolStripMenuItem.Size = new System.Drawing.Size(39, 20);
            this.izlazToolStripMenuItem.Text = "Kraj";
            // 
            // izlazAltIToolStripMenuItem
            // 
            this.izlazAltIToolStripMenuItem.Name = "izlazAltIToolStripMenuItem";
            this.izlazAltIToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Alt | System.Windows.Forms.Keys.I)));
            this.izlazAltIToolStripMenuItem.Size = new System.Drawing.Size(132, 22);
            this.izlazAltIToolStripMenuItem.Text = "Izlaz ";
            this.izlazAltIToolStripMenuItem.Click += new System.EventHandler(this.izlazAltIToolStripMenuItem_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 262);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "Form1";
            this.Text = "Pocetna strana";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem unosToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem unosToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem periodiToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem lokalitetiToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem poArhiologuToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem poTipuAkvititetaToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem izlazToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem izlazAltIToolStripMenuItem;
    }
}

